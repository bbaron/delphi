package app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.Objects;

import static java.util.Objects.*;

@Configuration
@RequiredArgsConstructor
public class AclConfiguration {
  private final DataSource dataSource;

  @Bean
  MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
    var bean = new DefaultMethodSecurityExpressionHandler();
    bean.setPermissionEvaluator(new AclPermissionEvaluator(aclService()));
    bean.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
    return bean;
  }

  @Bean
  JdbcMutableAclService aclService() {
    return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
  }

  @Bean
  LookupStrategy lookupStrategy() {
    return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(),
        new ConsoleAuditLogger());
  }

  @Bean
  AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Bean
  AclCache aclCache() {
    var cache = requireNonNull(aclEhCacheFactoryBean().getObject());
    return new EhCacheBasedAclCache(cache, permissionGrantingStrategy(),
        aclAuthorizationStrategy());
  }

  @Bean
  EhCacheFactoryBean aclEhCacheFactoryBean() {
    return new EhCacheFactoryBean();
  }

//  @Bean
//  EhCacheManagerFactoryBean aclEhCacheManagerFactoryBean() {
//    return new EhCacheManagerFactoryBean();
//  }

  @Bean
  PermissionGrantingStrategy permissionGrantingStrategy() {
    return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
  }

}
