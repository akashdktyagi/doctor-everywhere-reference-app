package com.automationfraternity.cucumber;

import com.google.common.base.Throwables;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class JiraXRayListener implements ConcurrentEventListener {
    static String username = "akashdktyagi@gmail.com";
    static String token = "";

    public static void main(String[] args) throws URISyntaxException {
//        List<String> tags = event.getTestCase().getTags();
        String url = "https://akashdktyagi.atlassian.net/rest/raven/2.0/api/testexec/DE-5/test";
        String body = "{\n" +
                "    \"add\": [\n" +
                "        \"DE-2\"\n" +
                "    ]\n" +
                "}";
        WebClient client = WebClient.create();
        String response = client.post()
                .uri(new URI(url))
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString((username + ":" + token).getBytes(UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
            eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStarted);
            eventPublisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
            eventPublisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinish);
            eventPublisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
    }

    /**
     * Create Test Execution ID here
     * @param event
     */
    private void testRunStarted(TestRunStarted event)  {
        try {
            String url = "https://akashdktyagi.atlassian.net/rest/api/3/issue";
            String body = "{\n" +
                    "    \"fields\": {\n" +
                    "        \"summary\": \"New Test Execution\",\n" +
                    "        \"project\": {\n" +
                    "            \"key\": \"DE\"\n" +
                    "        },\n" +
                    "        \"issuetype\": {\n" +
                    "            \"name\": \"Test Execution\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            WebClient client = WebClient.create();
            String response = client.post()
                    .uri(new URI(url))
                    .header("Authorization", "Basic " + Base64Utils
                            .encodeToString((username + ":" + token).getBytes(UTF_8)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(body))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Link Test Case ID, with Test Exec ID
     * Link Test Case ID with User Story
     * @param event
     */
    private void testCaseStarted(TestCaseStarted event){
//        List<String> tags = event.getTestCase().getTags();
//        String url = "https://akashdktyagi.atlassian.net/rest/raven/1.0/api/testexec/{{testExecID}}/test";
//        String body = "{\n" +
//                "    \"fields\": {\n" +
//                "        \"summary\": \"New Test Execution\",\n" +
//                "        \"project\": {\n" +
//                "            \"key\": \"DE\"\n" +
//                "        },\n" +
//                "        \"issuetype\": {\n" +
//                "            \"name\": \"Test Execution\"\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//        WebClient client = WebClient.create();
//        String response = client.post()
//                .uri(new URI(url))
//                .header("Authorization", "Basic " + Base64Utils
//                        .encodeToString((username + ":" + token).getBytes(UTF_8)))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(body))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

    }

    private void testCaseFinish(TestCaseFinished event){



    }

    private void testRunFinished(TestRunFinished event){

    }

    private String getTestSteps(TestCaseFinished event){
        List<TestStep> testStepList = event.getTestCase().getTestSteps();
        StringBuffer testStepListString = new StringBuffer();
        testStepList.forEach(x->{
            if (x instanceof PickleStepTestStep){
                testStepListString.append(
                    ((PickleStepTestStep) x).getStep().getKeyword() + " " +
                    ((PickleStepTestStep) x).getStep().getText() + " \n"
                );
                if (((PickleStepTestStep) x).getStep().getArgument() != null) {
                    if (((PickleStepTestStep) x).getStep().getArgument() instanceof DocStringArgument) {

                        testStepListString.append(
                                ("\"\"\"\n") +
                                        (((DocStringArgument) ((PickleStepTestStep) x).getStep().getArgument()).getContent()) +
                                        ("\n\"\"\"\n")
                        );
                    }
                    if (((PickleStepTestStep) x).getStep().getArgument() instanceof DataTableArgument) {

                        List<List<String>> ParamDataTable = (((DataTableArgument) ((PickleStepTestStep) x).getStep().getArgument()).cells());
                        ParamDataTable.forEach(ParamRow -> {
                                    ParamRow.forEach(ParamCol -> {
                                        testStepListString.append(
                                                "|" + ParamCol
                                        );
                                    });
                                    testStepListString.append("|\n");
                                }
                        );
                    }
                }

            }
        });
        return testStepListString.toString();
    }

    private StringBuffer getTestCaseStepsWithStatusAndErrorLogs(TestCaseFinished event){
        StringBuffer testStepListString = new StringBuffer();
        testStepListString.append("Scenario Steps: ");
        testStepListString.append("\n Scenario: " + event.getTestCase().getName() + " \n \n");
        testStepListString.append(getTestSteps(event));

        testStepListString.append("\n \n ----------------------------------------------------------");
        testStepListString.append("\n --------------------  ERROR LOGS  -----------------------");
        testStepListString.append("\n  ----------------------------------------------------------");
        if (event.getResult().getError() !=null){
            testStepListString.append("\n Execution Error Log: \n \n " + Throwables.getStackTraceAsString(event.getResult().getError()));
        }
        return testStepListString;
    }

}