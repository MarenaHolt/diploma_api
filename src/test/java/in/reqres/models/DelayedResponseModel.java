package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DelayedResponseModel {
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    private Integer total;
    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("data")
    List<UserModel> users;

    @JsonProperty("support")
    SupportModel support;
}
