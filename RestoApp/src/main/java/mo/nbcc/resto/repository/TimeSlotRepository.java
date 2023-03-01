package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
	public List<TimeSlot> findByEvent(Event event);
	
}
