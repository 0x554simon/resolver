/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.shrinkwrap.resolver.impl.maven.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.shrinkwrap.resolver.api.maven.MavenResolutionFilter;
import org.jboss.shrinkwrap.resolver.api.maven.dependency.DependencyDeclaration;

/**
 * A combinator for multiple filters.
 *
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class CombinedFilter implements MavenResolutionFilter {
    private List<MavenResolutionFilter> filters;

    /**
     * Combines multiple filters in a such way that all must pass.
     *
     */
    public CombinedFilter(MavenResolutionFilter... filters) {
        this.filters = new ArrayList<MavenResolutionFilter>(filters.length);
        this.filters.addAll(Arrays.asList(filters));
    }

    @Override
    public MavenResolutionFilter setDefinedDependencies(List<DependencyDeclaration> dependencies) {
        for (MavenResolutionFilter f : filters) {
            f.setDefinedDependencies(dependencies);
        }
        return this;
    }

    @Override
    public MavenResolutionFilter setDefinedDependencyManagement(List<DependencyDeclaration> dependencyManagement) {
        for (MavenResolutionFilter f : filters) {
            f.setDefinedDependencyManagement(dependencyManagement);
        }
        return this;
    }

    @Override
    public boolean accepts(DependencyDeclaration coordinate) throws IllegalArgumentException {
        for (MavenResolutionFilter f : filters) {
            if (f.accepts(coordinate) == false) {
                return false;
            }
        }
        return true;
    }

}
