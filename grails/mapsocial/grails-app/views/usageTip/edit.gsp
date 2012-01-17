

<%@ page import="org.mappu.UsageTip" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usageTip.label', default: 'UsageTip')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${usageTipInstance}">
            <div class="errors">
                <g:renderErrors bean="${usageTipInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <g:hiddenField name="id" value="${usageTipInstance?.id}" />
                <g:hiddenField name="version" value="${usageTipInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="language"><g:message code="usageTip.language.label" default="Language" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'language', 'errors')}">
                                    <g:textField name="language" maxlength="2" value="${usageTipInstance?.language}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="usageTip.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${usageTipInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="text"><g:message code="usageTip.text.label" default="Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'text', 'errors')}">
                                    <g:textArea name="text" cols="40" rows="5" value="${usageTipInstance?.text}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mimeType"><g:message code="usageTip.mimeType.label" default="Mime Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'mimeType', 'errors')}">
                                    <g:textField name="mimeType" readonly="readonly" value="${usageTipInstance?.mimeType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="imageData"><g:message code="usageTip.imageData.label" default="Image Data" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usageTipInstance, field: 'imageData', 'errors')}">
                                    <input type="file" id="imageData" name="imageData" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
