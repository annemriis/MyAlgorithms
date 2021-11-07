package ee.ttu.algoritmid.flights;

public class CorrectFlightCrew implements FlightCrew {

    private FlightCrewMember pilot;
    private FlightCrewMember copilot;
    private FlightCrewMember flightAttendant;

    public CorrectFlightCrew(FlightCrewMember pilot, FlightCrewMember copilot, FlightCrewMember flightAttendant) {
        this.pilot = pilot;
        this.copilot = copilot;
        this.flightAttendant = flightAttendant;
    }

    @Override
    public FlightCrewMember getPilot() {
        return pilot;
    }

    @Override
    public FlightCrewMember getCopilot() {
        return copilot;
    }

    @Override
    public FlightCrewMember getFlightAttendant() {
        return flightAttendant;
    }
}
