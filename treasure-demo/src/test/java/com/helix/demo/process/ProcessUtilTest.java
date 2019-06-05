package com.helix.demo.process;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessUtilTest {

	private static Logger logger = LoggerFactory.getLogger(ProcessUtilTest.class);
	
	private static String scriptPath = "D:/develop/java/workspace/git-workspace/pratice-demo/treasure/treasure-demo/src/test/resources/com/helix/demo/process/";
	@Test
	public void processUtilTest() throws Exception{
		String commandPath = scriptPath +  "start-sleep.bat";
		//注意command是常规命令不会有路径，如果是一个bat文件，则需要绝对路径或者设置process.workingdir属性
		StringBuffer command =  new StringBuffer("cmd /c "+commandPath);
//		StringBuffer command =  new StringBuffer("cmd /c "+" chdir ");

		Long timeout = 25*1000L;
		ProcessHandlerResult handlerResult = ProcessUtil.buildProcessHandlerResult();
		logger.debug("process begin");
		ProcessUtil.exec(command.toString(), timeout, handlerResult);
		Thread.sleep(1000L);
		logger.debug("hasResult:{},exitValue:{}",handlerResult.isHasResult(),handlerResult.getExitCode());
		Thread.sleep(30000L);
		logger.debug("hasResult:{},exitValue:{}",handlerResult.isHasResult(),handlerResult.getExitCode());
	}
	
}
