
<%@ page import="org.mappu.UsageTip" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usageTip.label', default: 'UsageTip')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'usageTip.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="language" title="${message(code: 'usageTip.language.label', default: 'Language')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'usageTip.title.label', default: 'Title')}" />
                        
                            <g:sortableColumn property="text" title="${message(code: 'usageTip.text.label', default: 'Text')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usageTipInstanceList}" status="i" var="usageTipInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${usageTipInstance.id}">${fieldValue(bean: usageTipInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: usageTipInstance, field: "language")}</td>
                        
                            <td>${fieldValue(bean: usageTipInstance, field: "title")}</td>
                        
                            <td>${fieldValue(bean: usageTipInstance, field: "text")}</td>
                                                
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${usageTipInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
