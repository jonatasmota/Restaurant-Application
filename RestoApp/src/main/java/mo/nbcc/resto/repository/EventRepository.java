package mo.nbcc.resto.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	public List<Event> findByEventName(String eventName);
	public List<Event> findByEventFloorPlanIsNull();
	public List<Event> findByEventDate(LocalDate date);
	
	//Used to populate a list of only future events
	@Query("SELECT e FROM Event e WHERE e.eventDate > :date")
	public List<Event> findEventsAfterDate(
			@Param("date") LocalDate date);
	
	//Search Event
	public List<Event> findByEventNameContainingIgnoreCase(String eventName);
}
