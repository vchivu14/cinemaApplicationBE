package cinema.shows.services;

import cinema.shows.dtos.ActorDTO;
import cinema.shows.entities.Actor;
import cinema.shows.repos.ActorRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActorServicesImp implements ActorServices {
    private ActorRepo actorRepo;

    public ActorServicesImp(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    @Override
    public List<ActorDTO> getListOfActorsToShowWithMovieRequest(Set<Actor> actors) {
        List<ActorDTO> actorDTOS = new ArrayList<>();
        for (Actor a: actors) {
            ActorDTO actorDTO = new ActorDTO(a);
            actorDTOS.add(actorDTO);
        }
        return actorDTOS;
    }

    @Override
    public Set<Actor> getSetOfActorsFromListOfActorDTOs(List<ActorDTO> actorDTOList) {
        Set<Actor> actorsSet = new HashSet<>();
        for (ActorDTO a: actorDTOList) {
            Actor newActorSaved = getByFirstNameAndLastName(a.getFirstName(),a.getLastName());
            if (newActorSaved == null) {
                newActorSaved = actorRepo.save(new Actor(a));
            }
            actorsSet.add(newActorSaved);
        }
        return actorsSet;
    }

    @Override
    public List<Actor> saveAll(List<ActorDTO> actorDTOList) {
        List<Actor> actorList = new ArrayList<>();
        for (ActorDTO a: actorDTOList) {
            Actor actor = new Actor(a);
            actorList.add(actor);
        }
        return actorRepo.saveAll(actorList);
    }

    @Override
    public Actor getByFirstNameAndLastName(String firstName, String lastName) {
        return actorRepo.findActorByFirstNameAndLastName(firstName,lastName);
    }
}
