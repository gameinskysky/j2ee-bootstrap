package me.zjor.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
public class TestRunner extends BlockJUnit4ClassRunner {

    private Injector injector;

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @throws org.junit.runners.model.InitializationError
     *          if the test class is malformed.
     */
    public TestRunner(Class<?> classToRun) throws InitializationError {
        super(classToRun);
        if (injector == null) injector = Guice.createInjector(configModules());
    }

    private Module[] configModules() {
        return new Module[] {
                new JpaPersistModule("TestJpaUnit"),
                new TestGuiceModule()
        };
    }


    @Override
    public Object createTest() {
        return injector.getInstance(getTestClass().getJavaClass());
    }

    @Override
    protected void validateZeroArgConstructor(List<Throwable> errors) {
        // Guice can inject constructors with parameters so we don't want this method to trigger an error
    }

    /**
     * @return the Guice injector
     */
    @SuppressWarnings("unused")
    protected Injector getInjector() {
        return injector;
    }

}
