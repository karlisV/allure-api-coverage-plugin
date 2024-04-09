package org.karlisV;

import io.qameta.allure.CommonJsonAggregator2;
import io.qameta.allure.Constants;
import io.qameta.allure.Reader;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.core.ResultsVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ApiCoveragePlugin  extends CommonJsonAggregator2 implements Reader {

    private static final String BLOCK_NAME = "coverage";
    private static final String JSON_FILE_NAME = "coverage.json";

    public ApiCoveragePlugin() {
        super(JSON_FILE_NAME);
    }

    @Override
    public Map getData(List<LaunchResults> results) {
        for(LaunchResults result : results){
            Optional<Map> optionalCoverageInfo = getCoverageInfo(result);
            if (optionalCoverageInfo.isPresent()) {
                return optionalCoverageInfo.get();
            }
        }
        return new HashMap<>();
    }

    @Override
    public void readResults(final Configuration configuration,
                            final ResultsVisitor visitor,
                            final Path directory) {
        final JacksonContext context = configuration.requireContext(JacksonContext.class);
        final Path coverageFile = directory.resolve(JSON_FILE_NAME);
        if (Files.exists(coverageFile)) {
            try (InputStream is = Files.newInputStream(coverageFile)) {
                final Map coverageInfo = context.getValue().readValue(is, Map.class);
                visitor.visitExtra(BLOCK_NAME, coverageInfo);
            } catch (IOException e) {
                visitor.error("Could not read coverage file " + coverageFile, e);
            }
        }
    }

    private Optional<Map> getCoverageInfo(final LaunchResults results) {
        return results.getExtra(BLOCK_NAME);
    }

}
