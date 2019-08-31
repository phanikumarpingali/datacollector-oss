/*
 * Copyright 2019 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.processor.scripting;

import com.streamsets.pipeline.api.Stage;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.OnRecordErrorException;
import com.streamsets.pipeline.stage.common.ErrorRecordHandler;
import com.streamsets.pipeline.stage.util.scripting.Errors;
import com.streamsets.pipeline.stage.util.scripting.ScriptObjectFactory;
import com.streamsets.pipeline.stage.util.scripting.ScriptRecord;
import com.streamsets.pipeline.stage.util.scripting.ScriptingStageBindings;
import org.slf4j.Logger;

import java.util.Map;

public class ScriptingProcessorInitDestroyBindings extends ScriptingStageBindings {

  public final Object state;
  private final ErrorRecordHandler errorRecordHandler;
  public final Err error;


  public ScriptingProcessorInitDestroyBindings(
      ScriptObjectFactory scriptObjectFactory,
      Stage.Context context,
      ErrorRecordHandler errorRecordHandler,
      Map<String, String> userParams,
      Logger log,
      Object state
  ) {
    super(scriptObjectFactory, context, userParams, log);
    this.state = state;
    this.errorRecordHandler = errorRecordHandler;
    this.error = new Err();
  }

  public class Err {
    public void write(ScriptRecord scriptRecord, String errMsg) throws StageException {
      errorRecordHandler.onError(new OnRecordErrorException(
          scriptObjectFactory.getRecord(scriptRecord),
          Errors.SCRIPTING_04,
          errMsg
      ));
    }
  }
}
