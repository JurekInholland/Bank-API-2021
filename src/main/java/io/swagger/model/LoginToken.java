package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LoginToken
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-20T13:58:44.577Z[GMT]")


public class LoginToken   {
  @JsonProperty("authtoken")
  private String authtoken = null;

  public LoginToken authtoken(String authtoken) {
    this.authtoken = authtoken;
    return this;
  }

  /**
   * Get authtoken
   * @return authtoken
   **/
  @Schema(example = "xx508xx63817x752xx74004x30705xx92x58349x5x78f5xx34xxxxx51", required = true, description = "")
      @NotNull

    public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken = authtoken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginToken loginToken = (LoginToken) o;
    return Objects.equals(this.authtoken, loginToken.authtoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authtoken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginToken {\n");
    
    sb.append("    authtoken: ").append(toIndentedString(authtoken)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
