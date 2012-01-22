<html>
    <head>
        <title>Welcome to Mappu</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <div class="main-dialog">
           <div class="controller">
           		<p>
           			<g:link controller="linkUI">
           			<g:message code="index.links"/>
           			</g:link>
           			<br/><span></span>
           		</p>
           </div>
           <div class="controller">
           		<p>
    	       		<g:link controller="layerQueryUI">
    	       		<g:message code="index.searches"/>
    	       		</g:link>
           			<br/><span></span>
           		</p>
           </div>
           <div class="controller">
           		<p>
	           		<g:link controller="person">
	           		<g:message code="index.users"/>
	           		</g:link>
	           		<br/>
           			<span></span>
           		</p>
           </div>
           <div class="controller">
           		<p>
	           		<g:link controller="usageTip">
	           		<g:message code="index.tips"/></g:link>
	           		<br/>
           			<span></span>
           		</p>
           </div>
           <div class="bottomtext">
           	<g:message code="index.warn.admin"/>
           </div>
        </div>
    </body>
</html>