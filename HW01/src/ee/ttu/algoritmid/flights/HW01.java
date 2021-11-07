package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class HW01 implements FlightCrewRegistrationSystem {

    private AVLTree pilotsAVLTree = new AVLTree();
    private AVLTree copilotsAVLTree = new AVLTree();
    private AVLTree flightAttendantAVLTree = new AVLTree();

    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        if (!participantHasCorrectInformation(participant)) {
            throw new IllegalArgumentException();
        }
        FlightCrewMemberNode pilot = findPilot(participant);
        FlightCrewMemberNode copilot = findCopilot(participant);
        FlightCrewMemberNode flightAttendant = findFlightAttendant(participant);
        if (pilot == null || copilot == null || flightAttendant == null) {
            addParticipantToWaitingList(participant);
            return null;
        }
        removeCrewMembersFromWaitingList(pilot, copilot, flightAttendant);
        return new CorrectFlightCrew(pilot.getData(), copilot.getData(), flightAttendant.getData());
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        // TODO
        return null;
    }

    private boolean participantHasCorrectInformation(FlightCrewMember participant) {
        return !participant.getName().strip().equals("")
                && participant.getWorkExperience() >= 0
                && (participant.getRole().equals(FlightCrewMember.Role.PILOT)
                || participant.getRole().equals(FlightCrewMember.Role.COPILOT)
                || participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT));
    }

    private FlightCrewMemberNode findPilot(FlightCrewMember participant) {
        if (participant.getRole().equals(FlightCrewMember.Role.COPILOT)) {
            return pilotsAVLTree
                    .findElementGreaterAtLeastByK1(pilotsAVLTree.getRootNode(), 5, 10, participant.getWorkExperience());
        } else if (participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
            return pilotsAVLTree
                    .findElementGreaterAtLeastByK1(pilotsAVLTree.getRootNode(), 8, 13, participant.getWorkExperience());
        }
        return new FlightCrewMemberNode(participant);
    }

    private FlightCrewMemberNode findCopilot(FlightCrewMember participant) {
        if (participant.getRole().equals(FlightCrewMember.Role.PILOT)) {
            return copilotsAVLTree
                    .findElementLessAtLeastByK1(copilotsAVLTree.getRootNode(), 5, 10, participant.getWorkExperience());
        } else if (participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
            return copilotsAVLTree
                    .findElementGreaterAtLeastByK1(copilotsAVLTree.getRootNode(), 3, Integer.MAX_VALUE, participant.getWorkExperience());
        }
        return new FlightCrewMemberNode(participant);
    }

    private FlightCrewMemberNode findFlightAttendant(FlightCrewMember participant) {
        if (participant.getRole().equals(FlightCrewMember.Role.PILOT)) {
            return flightAttendantAVLTree
                    .findElementLessAtLeastByK1(flightAttendantAVLTree.getRootNode(), 8, 13, participant.getWorkExperience());
        } else if (participant.getRole().equals(FlightCrewMember.Role.COPILOT)) {
            return flightAttendantAVLTree
                    .findElementLessAtLeastByK1(flightAttendantAVLTree.getRootNode(), 3, Integer.MAX_VALUE, participant.getWorkExperience());
        }
        return new FlightCrewMemberNode(participant);
    }

    private void addParticipantToWaitingList(FlightCrewMember participant) {
        if (participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
            flightAttendantAVLTree.insert(participant);
        } else if (participant.getRole().equals(FlightCrewMember.Role.COPILOT)) {
            copilotsAVLTree.insert(participant);
        }
        pilotsAVLTree.insert(participant);
    }

    private void removeCrewMembersFromWaitingList(FlightCrewMemberNode pilot, FlightCrewMemberNode copilot,
                                                  FlightCrewMemberNode flightAttendant) {
        pilotsAVLTree.remove(pilot);
        copilotsAVLTree.remove(copilot);
        flightAttendantAVLTree.remove(flightAttendant);
    }

    private List<FlightCrewMember> mergeAVLTrees() {
        List<FlightCrewMember> waitingList = new ArrayList<>();
        // while ()
        return waitingList;
    }
}