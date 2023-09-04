package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SingleUserResponseModel {

    @JsonProperty("data")
    UserModel user;

    @JsonProperty("support")
    SupportModel support;

}
