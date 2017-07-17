#language: en
@Ignore
@pipeline-overhead
@canaries
Feature: Pipeline Overhead Measurement

  As a CodePipeline developer
  I want to be able to measure overhead of the CodePipeline using no-op custom action
  So that I can monitor the CodePipeline service latency

  Background:
    Given I create the follocwing custom action:
      | ActionCategory | ActionProvider | ActionVersion | ConfigurationProperties                                                                              |
      | Source         | OverheadSource | 1             | [name:canaryRunId, required:true, key:true, secret:false, queryable:true, description:canary run id] |
      | Deploy         | OverheadAction | 1             | [name:canaryRunId, required:true, key:true, secret:false, queryable:true, description:canary run id] |

  Scenario: Pipeline execution completed and overhead time is measured
    When I create a pipeline with the following structure:
      | Stage | ActionName | ActionCategory | ActionOwner | ActionProvider | RunOrder | InputArtifacts | OutputArtifacts | Configuration           |
      | Alpha | source     | Source         | Custom      | OverheadSource | 1        |                | artifact        | canaryRunId:[testRunId] |
      | Beta  | myCustom   | Deploy         | Custom      | OverheadAction | 1        | artifact       |                 | canaryRunId:[testRunId] |
#    And I create a new revision in my source bucket
#    Then the custom action job worker will eventually poll the job within 60 minutes and put success job result
#    And the last execution status of Stage 'Beta' should eventually be 'Succeeded' within 60 minutes without ever being 'Failed'