/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.example

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * The entry point into the system.  Runs as a standalone web server.
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
@EnableDiscoveryClient
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

/*
    @Bean
    static FeedbackAwareBeanPostProcessor feedbackAwareBeanPostProcessor( ApplicationProperties configuration ) {
        new FeedbackAwareBeanPostProcessor( configuration.logging.serviceCode, configuration.logging.serviceInstance, configuration.logging.realm )
    }
*/

    // global CORS handler that lets anything from anywhere in
    @Bean
    WebMvcConfigurer corsConfigurer() {
        new WebMvcConfigurerAdapter() {
            @Override
            void addCorsMappings( CorsRegistry registry ) {
                registry.addMapping( '/**' ).allowedOrigins( '*' ).allowedMethods( 'GET', 'POST', 'DELETE', 'PUT', 'OPTIONS' )
            }
        }
    }

    @Bean
    Exchange exchange() {
        ExchangeBuilder.fanoutExchange( 'Complaints' ).build()
    }

    @Bean
    Queue queue() {
        QueueBuilder.durable( 'Complaints' ).build()
    }

    @Bean
    Binding binding( Queue queue, Exchange exchange ) {
        BindingBuilder.bind( queue ).to( exchange ).with( '*' ).noargs()
    }

    // Do not make this static or the binding will not be declared
    // TODO: figure out exactly why we need this.  Is it because we don't have a consumer set up yet?
    @Autowired
    void configure( AmqpAdmin admin, Exchange exchange, Queue queue, Binding binding ) {
        admin.declareExchange( exchange )
        admin.declareQueue( queue )
        admin.declareBinding( binding )
    }
}
