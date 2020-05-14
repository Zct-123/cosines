
package com.chatopera.cc.aspect;

import com.chatopera.cc.cache.Cache;
import com.chatopera.cc.model.AgentStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
public class AgentStatusAspect {
    private final static Logger logger = LoggerFactory.getLogger(AgentStatusAspect.class);

    @Autowired
    private Cache cache;

    @After("execution(* com.chatopera.cc.persistence.repository.AgentStatusRepository.save(..))")
    public void save(final JoinPoint joinPoint) {
        final AgentStatus agentStatus = (AgentStatus) joinPoint.getArgs()[0];
        cache.putAgentStatusByOrgi(agentStatus, agentStatus.getOrgi());
    }

    @After("execution(* com.chatopera.cc.persistence.repository.AgentStatusRepository.delete(..))")
    public void delete(final JoinPoint joinPoint) {
        final AgentStatus agentStatus = (AgentStatus) joinPoint.getArgs()[0];
        cache.deleteAgentStatusByAgentnoAndOrgi(agentStatus.getAgentno(), agentStatus.getOrgi());
    }
}
