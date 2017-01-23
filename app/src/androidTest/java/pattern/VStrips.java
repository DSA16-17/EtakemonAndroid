package pattern;

import java.util.ArrayList;
import java.util.List;

import dsa.initial.Patterns;

/**
 * Created by Turpitude on 05/01/2017.
 */

public class VStrips implements Patterns {
    List<Boolean> pattern = new ArrayList<Boolean>(){{

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
        add(false);

    }};

    @Override
    public List<Boolean> setPattern() {
        return pattern;
    }
    @Override
    public String getClassName() {
        return this.getClass().getName();
    }
}
