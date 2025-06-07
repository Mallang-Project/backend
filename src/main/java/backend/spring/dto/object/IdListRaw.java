package backend.spring.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record IdListRaw(
        @JsonProperty("results") List<Item> results) {

    public List<String> toIdList() {
        return results.stream()
                .map(item -> String.valueOf(item.id()))
                .toList();
    }

    public record Item(@JsonProperty("id") Long id) {}
}
