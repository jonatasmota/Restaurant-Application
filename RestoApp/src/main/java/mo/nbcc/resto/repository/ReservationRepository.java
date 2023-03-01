package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mo.nbcc.resto.model.Reservation;
import mo.nbcc.resto.model.TimeSlot;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	public List<Reservation> findByFirstName(String firstName);
	public List<Reservation> findByLastName(String lastName);
	public List<Reservation> findByFirstNameAndLastName(String firstName, String lastName);
	public Reservation findByConfirmationNumber(int confirmationNumber);
	public List<Reservation> findByTimeSlot(TimeSlot slot);
}
