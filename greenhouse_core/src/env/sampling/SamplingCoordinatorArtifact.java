// CArtAgO artifact code for project greenhouse_core

package sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cartago.*;
import utility.component.Component;
import utility.component.ComponentUtility;
import utility.sample.Sample;
import utility.sample.SamplesUtility;
import utility.setting.RangeSetting;
import utility.setting.Settings;

/**
 * SamplingCoordinator Artifact is an Artifact used to coordinate 
 * all the sampling operations of the application.
 * Once everything is set up, every samplingCoordinatorAgent that is focusing on this
 * Artifact will start the sampling operation.
 * It is used to trigger the plan in the sampling artifact in order
 * to retrieve the samples.
 * When the samples are retrieved, the update of the information is going
 * to be handled here (uploading the data
 * to the persistence service and starting an actuator if needed).
 */
public class SamplingCoordinatorArtifact extends Artifact {

	//the delta between the last sample and the current sample accepted
	private static final int DELTA = 1;

	private List<Component> components;
	private Optional<Settings> settings;
	private List<Sample> lastSamples;

	void init() {
		this.components = new ArrayList<>();
		this.settings = Optional.empty();
		this.lastSamples = new ArrayList<>();
	}

	/**
	 * Operation used to set up the list of components when they are retrieved, which 
	 * are the components that the greenhouse can handle.
	 * 
	 * @param components the list of [[utility.component.Component]] found.
	 */
	@OPERATION
	void setupComponents(final List<Component> components) {
		this.components = components;
	}

	/**
     * Operation used to set up the list of settings when they are retrieved,
     * which are the settings created by the user.
     * 
     * @param settings an optional of [[utility.setting.Settings]] which contains
     *                 the settings only if they exist.
     */
	@OPERATION
	void setupSettings(final Optional<Settings> settings) {
		this.settings = settings;
	}

	/**
	 * Operation used to actually perform a sample. 
	 * A specific samplingAgent, every interval of time, starts a sampling operation
	 * for a specific category of components. 
	 * This starts the communication with the componentes 
	 * performed by the SamplingAgent.
	 * 
	 * @param category the category is the one of the components
	 *                 of which the samples are going to be retreived.
	 */
	@OPERATION
	void sampleOperation(final String category) {
		List<Component> filteredComponents = ComponentUtility.getComponentsByCategory(this.components, category);
		if (!filteredComponents.isEmpty()) {
			defineObsProperty("sampling", filteredComponents);
		}
	}

	/**
	 * Operation used to update the current value of the samples, and to decide if
	 * it is necessary to send the sample recorded to the persistence service 
	 * and to check if an actuator has to be triggered.
	 * 
	 * @param currentSamples the samples retrieved from the components 
	 * 						 of a specific category in the greenhouse.
	 */
	@OPERATION
	void updateOperation(final List<Sample> currentSamples) {
		if (!currentSamples.isEmpty()) {
			Sample averageSample = SamplesUtility.getAverageOfSamples(currentSamples);
			String category = averageSample.getCategory();
			Optional<Sample> lastSample = SamplesUtility.getSampleByCategory(lastSamples, category);
			System.out.println("Average current Sample: " + averageSample + "\nLast average sample: " + lastSample);

			uploadToPersistence(averageSample, lastSample);
			startActuator(averageSample, category);

			this.lastSamples.add(averageSample);
		}
	}

	/**
	 * Utility method used to check the average of the current samples and the last
	 * sample retrieved. If their difference is bigger that the specified DELTA,
	 * then the new average sample is going to be uploaded in the persistence
	 * service.
	 * 
	 * @param averageSample the average of the current samples retrieved.
	 * @param lastSample    the last sample saved.
	 */
	private void uploadToPersistence(final Sample averageSample, final Optional<Sample> lastSample) {
		if (lastSample.isPresent()) {
			if (isSampleDifferenceBiggerThanDelta(averageSample, lastSample)) {
				defineObsProperty("uploadPersistence", averageSample);
				lastSamples.remove(lastSample.get());
			}	
		}
	}

	/**
	 * Utility method used to check if the difference of the current average sample
	 * and the last sample is bigger than the DELTA.
	 * 
	 * @param averageSample the average of the current samples retrieved.
	 * @param lastSample    the last sample saved.
	 * @return true if the difference of the samples is bigger thant the DELTA, false otherwise.
	 */
	private boolean isSampleDifferenceBiggerThanDelta(final Sample averageSample, final Optional<Sample> lastSample) {
		return Math.abs(averageSample.getValue() - lastSample.get().getValue()) > DELTA;
	}

	/**
	 * Utility method used to check the settings to verify if it occurred an
	 * out of range sample and that it is necessary to perform an action
	 * from an actuator.
	 * 
	 * @param averageSample the average of the current samples retrieved.
	 * @param category      the category of the current average sample.
	 */
	private void startActuator(final Sample averageSample, final String category) {
		if (this.settings.isPresent()) {
			if (isRangeSettingPresent(category)) {
				RangeSetting rangeSetting = (RangeSetting) this.settings.get().getSetting(category).get();
				if (isSampleOutOfRange(averageSample, rangeSetting)) {
					System.out.println("Create out of range behavior");
					defineObsProperty("actuate", category, averageSample, rangeSetting);
				}
			}
		}
	}

	/**
	 * Utility method used check if the current average sample is out of the minimum and
	 * maximum define by the [[utility.setting.RangeSetting]] of its category.
	 * 
	 * @param averageSample the average of the current samples retrieved.
	 * @param rangeSetting  the range setting that define the minimum and the maximum
	 * 					    value of the sample of a specific category.
	 * @return true if the value of the average sample is out of range, false otherwise.
	 */
	private boolean isSampleOutOfRange(final Sample averageSample, final RangeSetting rangeSetting) {
		return averageSample.getValue() < rangeSetting.getMin() || averageSample.getValue() > rangeSetting.getMax();
	}

	/**
	 * Utility method used to verify if it exists a RangeSetting
	 * in the current settings list of the specified category.
	 * 
	 * @param category the category of the RangeSetting searched.
	 * @return true if a RangeSetting of the category specified exists,
	 * false otherwise.
	 */
	private boolean isRangeSettingPresent(final String category) {
		Settings currentSettings = this.settings.get();
		return (currentSettings.getSetting(category).isPresent() 
				&& currentSettings.getSetting(category).get() instanceof RangeSetting);
	}

}
