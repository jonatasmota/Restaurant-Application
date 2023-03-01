package mo.nbcc.resto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.repository.TimeSlotRepository;

@Service
public class TimeSlotService {

	private TimeSlotRepository tsr;

	@Autowired
	public TimeSlotService(TimeSlotRepository tsr) {
		super();
		this.tsr = tsr;
	}
	
	public List<TimeSlot> getTimeSlots() {
		return tsr.findAll();
	}
	
	public TimeSlot saveTimeSlot(TimeSlot t) {
		return tsr.save(t);
	}
	
	public TimeSlot getTimeSlotByID(Long id) {
		return tsr.getReferenceById(id);
	}
	
	public void deleteTimeSlotByID(Long id) {
		tsr.deleteById(id);
	}
	
	public List<TimeSlot> getTimeSlotsByEvent(Event event) {
		return tsr.findByEvent(event);
	}
}
