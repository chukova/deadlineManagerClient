package cz.cvut.fit.tjv.chukavol.client.resource;

import cz.cvut.fit.tjv.chukavol.client.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.client.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentResource {

    private final RestTemplate restTemplate;

    public StudentResource(RestTemplateBuilder builder,
                             @Value( "http://localhost:8080/" ) String apiUrl) {
        restTemplate = builder.rootUri(apiUrl + "/student").build();
    }

    private static final String page_uri = "/all/?page={page}&size={size}";
    private static final String one_uri  = "/{id}";


    public StudentDTO readById(int id){
        return restTemplate.getForObject(one_uri, StudentDTO.class,id);
    }

    public List<StudentDTO> readPage(int page, int size){
        return  restTemplate.exchange(page_uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<StudentDTO>>(){}, page, size).getBody();
    }

    public List<StudentDTO> findBySubjectId(int subjectId){
        return restTemplate.exchange("/all/{subjectId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<StudentDTO>>(){}, subjectId).getBody();
    }

    public URI create(StudentCreateDTO studentCreateDTO){
        return restTemplate.postForLocation("/",studentCreateDTO);
    }


    public void update(int id ,StudentCreateDTO studentCreateDTO){
        restTemplate.put(one_uri,studentCreateDTO,
                id);
    }

    public void delete(int id){
        restTemplate.delete(one_uri,id);
    }

}
