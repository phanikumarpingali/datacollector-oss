/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.datacollector.client.cli.command.manager;

import com.streamsets.datacollector.client.api.ManagerApi;
import com.streamsets.datacollector.client.cli.command.BaseCommand;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

@Command(name = "snapshot-capture", description = "Capture Snapshot")
public class SnapshotCaptureCommand extends BaseCommand {
  @Option(
    name = {"-n", "--name"},
    description = "Pipeline Name",
    required = true
  )
  public String pipelineName;

  @Option(
    name = {"-r", "--revision"},
    description = "Pipeline Revision",
    required = false
  )
  public String pipelineRev;

  @Option(
    name = {"-s", "--snapshot-name"},
    description = "Snapshot Name",
    required = true
  )
  public String snapshotName;

  @Option(
      name = {"-l", "--snapshot-label"},
      description = "Snapshot Label",
      required = false
  )
  public String snapshotLabel;

  @Option(
    name = {"-b", "--batches"},
    description = "Number of batches",
    required = false
  )
  public int batches;

  @Option(
    name = {"-B", "--batch-size"},
    description = "Batch Size",
    required = false
  )
  public int batchSize;

  @Override
  public void run() {
    if(pipelineRev == null) {
      pipelineRev = "0";
    }

    if(batches == 0) {
      batches = 1;
    }

    if(batchSize == 0) {
      batchSize = 10;
    }

    if(snapshotLabel == null) {
      snapshotLabel = snapshotName;
    }

    try {
      ManagerApi managerApi = new ManagerApi(getApiClient());
      managerApi.captureSnapshot(pipelineName, snapshotName, snapshotLabel, pipelineRev, batches, batchSize);
      System.out.println("Capture Snapshot command executed successfully");
    } catch (Exception ex) {
      if(printStackTrace) {
        ex.printStackTrace();
      } else {
        System.out.println(ex.getMessage());
      }
    }
  }
}
