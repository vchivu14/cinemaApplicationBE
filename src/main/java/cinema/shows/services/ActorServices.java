package cinema.shows.services;

import cinema.shows.dtos.ActorDTO;
import cinema.shows.entities.Actor;

import java.util.List;
import java.util.Set;

public interface ActorServices {
    List<ActorDTO> getListOfActorsToShowWithMovieRequest(Set<Actor> actors);
    Set<Actor> getSetOfActorsFromListOfActorDTOs(List<ActorDTO> actorDTOList);
    List<Actor> saveAll(List<ActorDTO> actorDTOList);
}
