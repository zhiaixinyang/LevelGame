package com.mdove.levelgame.net;

import java.util.Map;

/**
 * Created by MDove on 18/2/14.
 */
public interface PublicParamsGenerator<T, W> {

    Map<T, W> generate();
}
