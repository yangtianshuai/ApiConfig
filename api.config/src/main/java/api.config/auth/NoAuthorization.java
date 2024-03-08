package api.config.auth;

import java.lang.annotation.*;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuthorization {

}
