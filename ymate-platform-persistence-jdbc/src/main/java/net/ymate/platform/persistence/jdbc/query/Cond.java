/*
 * Copyright 2007-2107 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.jdbc.query;

import org.apache.commons.lang.StringUtils;

/**
 * 条件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/9 下午8:12
 * @version 1.0
 */
public class Cond {

    public enum OPT {
        EQ("="),
        NOT_EQ("!="),
        LT("<"),
        GT(">"),
        LT_EQ("<="),
        GT_EQ(">="),
        LIKE("LIKE");

        private String __opt;

        OPT(String opt) {
            this.__opt = opt;
        }

        @Override
        public String toString() {
            return __opt;
        }
    }

    private StringBuilder __condSB;

    /**
     * SQL参数集合
     */
    private Params __params;

    public static Cond create() {
        return new Cond();
    }

    private Cond() {
        __condSB = new StringBuilder();
        __params = Params.create();
    }

    public Params getParams() {
        return this.__params;
    }

    public Cond param(Object param) {
        this.__params.add(param);
        return this;
    }

    public Cond param(Params params) {
        this.__params.add(params);
        return this;
    }

    public Cond cond(String cond) {
        __condSB.append(" ").append(cond).append(" ");
        return this;
    }

    public Cond opt(String fieldA, OPT opt, String fieldB) {
        __condSB.append(fieldA).append(" ").append(opt).append(" ").append(fieldB);
        return this;
    }

    public Cond opt(String field, OPT opt) {
        __condSB.append(field).append(" ").append(opt).append(" ?");
        return this;
    }

    public Cond eq(String field) {
        return opt(field, OPT.EQ);
    }

    public Cond notEq(String field) {
        return opt(field, OPT.NOT_EQ);
    }

    public Cond gtEq(String field) {
        return opt(field, OPT.GT_EQ);
    }

    public Cond gt(String field) {
        return opt(field, OPT.GT);
    }

    public Cond ltEq(String field) {
        return opt(field, OPT.LT_EQ);
    }

    public Cond lt(String field) {
        return opt(field, OPT.LT);
    }

    public Cond like(String field) {
        return opt(field, OPT.LIKE);
    }

    public Cond and() {
        return cond("AND");
    }

    public Cond or() {
        return cond("OR");
    }

    public Cond not() {
        return cond("NOT");
    }

    public Cond bracketBegin() {
        return cond("(");
    }

    public Cond bracketEnd() {
        return cond(")");
    }

    public Cond in(String field, SQL subSql) {
        __condSB.append(field).append(" IN (").append(subSql.getSQL()).append(")");
        __params.add(subSql.getParams());
        return this;
    }

    public Cond in(String field, Params params) {
        __condSB.append(field).append(" IN (").append(StringUtils.repeat("?", ", ", params.getParams().size())).append(")");
        __params.add(params);
        return this;
    }

    @Override
    public String toString() {
        return __condSB.toString();
    }
}