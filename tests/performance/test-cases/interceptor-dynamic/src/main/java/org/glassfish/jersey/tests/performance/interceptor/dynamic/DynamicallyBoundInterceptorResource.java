/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.tests.performance.interceptor.dynamic;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.TimerContext;

import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

/**
 * Intercepted resource.
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 */
@Path(JerseyApp.ROOT_PATH)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class DynamicallyBoundInterceptorResource {

    private static final com.yammer.metrics.core.Timer postTimer =
            Metrics.newTimer(DynamicallyBoundInterceptorResource.class, "posts", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    private static final com.yammer.metrics.core.Timer getTimer =
            Metrics.newTimer(DynamicallyBoundInterceptorResource.class, "gets", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    private static final com.yammer.metrics.core.Timer putTimer =
            Metrics.newTimer(DynamicallyBoundInterceptorResource.class, "puts", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);

    @POST
    public String echo(final String text) {
        final TimerContext timer = postTimer.time();
        try {
            return text;
        } finally {
            timer.stop();
        }
    }

    @PUT
    public void put(final String text) {
        final TimerContext timer = putTimer.time();
        timer.stop();
    }

    @GET
    public String get() {
        final TimerContext timer = getTimer.time();
        try {
            return "text";
        } finally {
            timer.stop();
        }
    }
}
