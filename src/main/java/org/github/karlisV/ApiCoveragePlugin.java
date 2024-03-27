package org.github.karlisV;

import io.qameta.allure.CommonJsonAggregator2;
import io.qameta.allure.Reader;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.core.ResultsVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApiCoveragePlugin  extends CommonJsonAggregator2 implements Reader {

    private static final String BLOCK_NAME = "coverage";
    private static final String JSON_FILE_NAME = "coverage.json";


    protected ApiCoveragePlugin(String fileName) {
        super(fileName);
    }

    @Override
    protected List<Map> getData(List<LaunchResults> list) {
        return list.stream()
                .map(this::getPerformanceInfo)
                .filter(Optional::isPresent)
                .flatMap(optional -> {
                    List<Map> maps = (List<Map>) optional.get();
                    return maps.stream();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void readResults(final Configuration configuration,
                            final ResultsVisitor visitor,
                            final Path directory) {
        final JacksonContext context = configuration.requireContext(JacksonContext.class);
        final Path performanceFile = directory.resolve(JSON_FILE_NAME);
        if (Files.exists(performanceFile)) {
            try (InputStream is = Files.newInputStream(performanceFile)) {
                final Map info = context.getValue().readValue(is, Map.class);
                visitor.visitExtra(BLOCK_NAME, info);
            } catch (IOException e) {
                visitor.error("Could not read coverage file " + performanceFile, e);
            }
        }
    }

    private Optional<Map> getPerformanceInfo(final LaunchResults results) {
        return results.getExtra(BLOCK_NAME);
    }

}
