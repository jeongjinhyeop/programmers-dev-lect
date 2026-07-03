package org.example.feignapi.controller;

import jakarta.annotation.PostConstruct;
import org.example.feignapi.dto.DataRequest;
import org.example.feignapi.dto.DataResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private Map<Long, DataResponse> dataStore = new HashMap<>();
    private Long idCounter = 1L;

    @PostConstruct
    public void initDataStore() {
        dataStore.put(idCounter++, new DataResponse(1L, "item 1", 100));
        dataStore.put(idCounter++, new DataResponse(1L, "item 1", 200));
        dataStore.put(idCounter++, new DataResponse(1L, "item 1", 300));
        dataStore.put(idCounter++, new DataResponse(1L, "item 1", 400));
        dataStore.put(idCounter++, new DataResponse(1L, "item 1", 500));
    }

    @GetMapping("/{id}")
    public DataResponse getDataById(@PathVariable Long id) {

        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) throw new RuntimeException("Data not found " + id);

        return dataResponse;
    }

    @PostMapping
    public DataResponse createData(@RequestBody DataRequest dataRequest) {
//        DataResponse dataResponse = new DataResponse(idCounter++, dataRequest.getName(), dataRequest.getValue());
//        dataStore.put(idCounter, dataResponse);
        //위의 방식으로 사용하면 데이터 위치를 일일히 맞춰줘야하지만 아래는 알아서 매핑해줌
        //위의 휴먼에러를 줄여주는 방식이 builder
        DataResponse build = DataResponse.builder()
                .id(idCounter++)
                .value(dataRequest.getValue())
                .name(dataRequest.getName())
                .build();

        dataStore.put(build.getId(), build);

        return build;
    }

    @PostMapping("/{id}")
    public DataResponse updateData(
            //("id")는 없어도 되지만 공부할때 보기 편하게 적어놓음
            @PathVariable("id") Long id,
            @RequestBody DataRequest dataRequest
    ) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) throw new RuntimeException();

        dataResponse.setName(dataRequest.getName());
        dataResponse.setValue(dataRequest.getValue());
        dataStore.put(id, dataResponse);

        return dataResponse;
    }

    @DeleteMapping("/{id}")
    public String deleteData(@PathVariable Long id){
        DataResponse removed = dataStore.remove(id);

        if(removed == null) throw new RuntimeException();

        return "Data deleted with id: " + id;
    }
}
