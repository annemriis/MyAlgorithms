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
        FlightCrewMemberNode copilot = findCopilot(participant);
        FlightCrewMemberNode pilot = null;
        FlightCrewMemberNode flightAttendant = null;
        if (copilot != null) {
            pilot = findPilot(participant, copilot.getData());
            flightAttendant = findFlightAttendant(participant, copilot.getData());
        }
        if (pilot == null || flightAttendant == null) {
            addParticipantToWaitingList(participant);
            return null;
        }
        removeCrewMembersFromWaitingList(pilot, copilot, flightAttendant);
        return new CorrectFlightCrew(pilot.getData(), copilot.getData(), flightAttendant.getData());
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        return mergeAVLTrees();
    }

    private boolean participantHasCorrectInformation(FlightCrewMember participant) {
        return participant != null && !participant.getName().strip().equals("")
                && participant.getWorkExperience() >= 0
                && (participant.getRole().equals(FlightCrewMember.Role.PILOT)
                || participant.getRole().equals(FlightCrewMember.Role.COPILOT)
                || participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT));
    }

    private FlightCrewMemberNode findPilot(FlightCrewMember participant, FlightCrewMember copilot) {
        if (participant.getRole().equals(FlightCrewMember.Role.COPILOT)) {
            return pilotsAVLTree
                    .findElementGreaterAtLeastByK1(pilotsAVLTree.getRootNode(), 5, 10, participant.getWorkExperience());
        } else if (participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {
            return pilotsAVLTree
                    .findElementGreaterAtLeastByK1(pilotsAVLTree.getRootNode(), 5, 10, copilot.getWorkExperience());
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

    private FlightCrewMemberNode findFlightAttendant(FlightCrewMember participant, FlightCrewMember copilot) {
        if (participant.getRole().equals(FlightCrewMember.Role.PILOT)) {
            return flightAttendantAVLTree
                    .findElementLessAtLeastByK1(flightAttendantAVLTree.getRootNode(), 3, Integer.MAX_VALUE, copilot.getWorkExperience());
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
        } else {
            pilotsAVLTree.insert(participant);
        }
    }

    private void removeCrewMembersFromWaitingList(FlightCrewMemberNode pilot, FlightCrewMemberNode copilot,
                                                  FlightCrewMemberNode flightAttendant) {
        pilotsAVLTree.remove(pilot);
        copilotsAVLTree.remove(copilot);
        flightAttendantAVLTree.remove(flightAttendant);
    }

    private List<FlightCrewMember> mergeAVLTrees() {
        List<FlightCrewMember> waitingList = new ArrayList<>();
        List<FlightCrewMemberNode> pilots = pilotsAVLTree.inorderTraversal(pilotsAVLTree.getRootNode(), new ArrayList<>());
        List<FlightCrewMemberNode> copilots = copilotsAVLTree.inorderTraversal(copilotsAVLTree.getRootNode(), new ArrayList<>());
        List<FlightCrewMemberNode> flightAttendants = flightAttendantAVLTree.inorderTraversal(flightAttendantAVLTree.getRootNode(), new ArrayList<>());

        int waitingListSize = calculateWaitingListSize(pilots, copilots, flightAttendants);

        int pilotsIndex = 0;
        int copilotsIndex = 0;
        int flightAttendantsIndex = 0;
        while (waitingListSize != 0) {
            FlightCrewMemberNode pilot = null;
            FlightCrewMemberNode copilot = null;
            FlightCrewMemberNode flightAttendant = null;
            if (pilots.size() - 1 >= pilotsIndex) {
                pilot = pilots.get(pilotsIndex);
            }
            if (copilots.size() - 1 >= copilotsIndex) {
                copilot = copilots.get(copilotsIndex);
            }
            if (flightAttendants.size() - 1 >= flightAttendantsIndex) {
                flightAttendant = flightAttendants.get(flightAttendantsIndex);
            }

            FlightCrewMember flightCrewMember = flightCrewMemberWithSmallestExperience(pilot, copilot, flightAttendant).getData();
            if (flightCrewMember.getRole().equals(FlightCrewMember.Role.PILOT)) {
                pilotsIndex += 1;
            } else if (flightCrewMember.getRole().equals(FlightCrewMember.Role.COPILOT)) {
                copilotsIndex += 1;
            } else {
                flightAttendantsIndex += 1;
            }

            waitingList.add(flightCrewMember);
            waitingListSize -= 1;
        }
        return waitingList;
    }

    private int calculateWaitingListSize(List<FlightCrewMemberNode> pilots, List<FlightCrewMemberNode> copilots,
                                         List<FlightCrewMemberNode> flightAttendants) {
        return pilots.size() + copilots.size() + flightAttendants.size();
    }

    private FlightCrewMemberNode flightCrewMemberWithSmallestExperience(FlightCrewMemberNode pilot,
                                                                        FlightCrewMemberNode copilot,
                                                                        FlightCrewMemberNode flightAttendant) {
        if (pilot == null || copilot == null || flightAttendant == null) {
            return flightCrewMemberWithSmallestExperienceOneIsNull(pilot, copilot, flightAttendant);
        }
        double pilotExperience = pilot.getValue();
        double copilotExperience = copilot.getValue();
        double flightAttendantExperience = flightAttendant.getValue();
        if (flightAttendantExperience <= copilotExperience && flightAttendantExperience <= pilotExperience) {
            return flightAttendant;
        } else if (copilotExperience < flightAttendantExperience && copilotExperience <= pilotExperience) {
            return copilot;
        }
        return pilot;
    }

    private FlightCrewMemberNode flightCrewMemberWithSmallestExperienceOneIsNull(FlightCrewMemberNode pilot,
                                                                                 FlightCrewMemberNode copilot,
                                                                                 FlightCrewMemberNode flightAttendant) {
        if (pilot == null && copilot == null) {
            return flightAttendant;
        } else if (pilot == null && flightAttendant == null) {
            return copilot;
        } else if (copilot == null && flightAttendant == null) {
            return pilot;
        } else if ((pilot == null && flightAttendant.getValue() <= copilot.getValue())
                || (copilot == null && flightAttendant.getValue() <= pilot.getValue())) {
            return flightAttendant;
        } else if ((copilot == null && pilot.getValue() < flightAttendant.getValue())
                || (flightAttendant == null && pilot.getValue() < copilot.getValue())) {
            return pilot;
        }
        return copilot;
    }
}