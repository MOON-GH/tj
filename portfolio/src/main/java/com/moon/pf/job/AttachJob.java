package com.moon.pf.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.moon.pf.service.BoardAttachService;

public class AttachJob extends QuartzJobBean{
	@Autowired BoardAttachService baService;
	Logger logger = Logger.getLogger(AttachJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// DI를 하기 위한 코드
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		// baService.updateUnlinkedInfo(); 밑으로는 실행되지 않는다. 즉, 여러배치를 실행하려면 Beans 에 여러개 등록해야한다.
		baService.updateUnlinkedInfo();
	}
	
}