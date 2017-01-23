package pattern;

import java.util.ArrayList;
import java.util.List;

import dsa.eetac.upc.edu.etakemon.Patterns;

/**
 * Created by Turpitude on 05/01/2017.
 */

public class VStrips2 implements Patterns {

    List<Boolean> pattern = new ArrayList<Boolean>(){{

        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
        add(false);
        add(true);
    }};

    @Override
    public List<Boolean> setPattern() {
        return pattern;
    }
}
