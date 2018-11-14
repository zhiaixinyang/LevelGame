package com.mdove.levelgame.net;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by MDove on 18/2/14.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface NoEncrypt {
}
