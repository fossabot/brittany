// CArtAgO artifact code for project greenhouse_core

package communication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.query.algebra.evaluation.function.datetime.Hours;

import cartago.*;
import utility.Sample;
import utility.component.Component;
import utility.setting.HourSetting;
import utility.setting.RangeSetting;
import utility.setting.Settings;

/**
 * ActautionArtifact is an Artifact that is used to send a message to an actuator to 
 * handle when the current sample registered is bigger or lower than the setting of
 * a specific device category.
 *
 */
public class ActuationArtifact extends Artifact {
	
	private List<Component> components;
	private Optional<Settings> settings;

	void init() {
		this.components = new ArrayList<>();
		this.settings = Optional.empty();
	}


	@OPERATION
	void setup(final List<Component> components, final Optional<Settings> settings) {
		this.settings = settings;
		this.components = components;
	}

	@OPERATION 
	void checkSettings(final String category){
		if (!this.components.isEmpty() && this.settings.isPresent() && this.settings.get().getSetting(category).isPresent()){
			HourSetting setting = (HourSetting) this.settings.get().getSetting(category).get();
			List<Component> actuators = getActuatorsByCategory(category);
			if (setting.getFromTime().compareTo(LocalTime.now()) <= 0) {
				
			} else if (setting.getToTime().compareTo(LocalTime.now()) <= 0) {

			}
		}
	}

	
	private List<Component> getActuatorsByCategory(final String category) {
		return this.components.stream().filter(c -> c.getCategory().equals(category)).filter(c -> !c.getActions().isEmpty()).collect(Collectors.toList());
	}

	/**
	 * Used to check the current sample and to notify if it is bigger or lower that the settings.
	 * @param outOfRangeSample the sample is out of range base on the settings.
	 * @param setting the setting of the specific category of the sample.
	 */
	@OPERATION void actuate(final Sample outOfRangeSample, final RangeSetting setting) {
		int currentValue = outOfRangeSample.getValue();
		
		if(currentValue < setting.getMin()) {
			System.out.println("Do something to increase the value. \nCurrent value: " + currentValue + " Min value: " + setting.getMin());
		} else if (currentValue > setting.getMax()) {
			System.out.println("Do something to decrease the value. \nCurrent value: " + currentValue + " Max value: " + setting.getMax());
		} else {
			throw new IllegalStateException("If the actuator is called, the value must be greater than the max or lower than the min value");
		}
	}
}
