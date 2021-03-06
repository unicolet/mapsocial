
<%@ page import="org.mappu.UsageTip" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usageTip.label', default: 'UsageTip')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usageTipInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.language.label" default="Language" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usageTipInstance, field: "language")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="title"><g:message code="usageTip.enabled.label" default="Enabled" /></label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'enabled', 'errors')}">
                                  <g:checkBox name="enabled" value="${usageTipInstance?.enabled}" disabled="true"/>
                              </td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usageTipInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.text.label" default="Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usageTipInstance, field: "text")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.mimeType.label" default="Mime Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usageTipInstance, field: "mimeType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usageTip.imageData.label" default="Image Data" /></td>
                            
                            <td valign="top" class="value"><img src="<%=request.getContextPath()%>/tips/img/${usageTipInstance.id}" width="100" height="100"></td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${usageTipInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
