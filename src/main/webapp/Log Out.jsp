<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Log Out"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					splitter="false" maxSize="Infinity">
					<span data-dojo-type="dijit.layout.AccordionContainer"
						duration="200" persist="false"
						style="min-width: 1em; min-height: 1em; width: 100%; height: 100%; text-align: center;"><div
							data-dojo-type="dijit.layout.ContentPane" title="Log Out"
							extractContent="false" preventCache="false" preload="false"
							refreshOnShow="false" doLayout="true" closable="false"
							splitter="false" maxSize="Infinity">
							<fieldset
								style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
								<legend style="font-weight: bold;"> Do you want to Log
									Out?</legend>
								<input type="button" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="No" iconClass="dijitNoIcon"></input>
								<input type="button" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="Yes" iconClass="dijitNoIcon"></input>
							</fieldset>
						</div>
						<div data-dojo-type="dijit.layout.ContentPane" title="Exit"
							extractContent="false" preventCache="false" preload="false"
							refreshOnShow="false" doLayout="true" selected="selected"
							closable="false" splitter="false" maxSize="Infinity">
							<fieldset
								style="border: 2px groove threedface; margin: 2px; padding: 0.75em; text-align: center;">
								<legend style="font-weight: bold;"> Do you want to
									Exit?</legend>
								<input type="button" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="No" iconClass="dijitNoIcon"></input>
								<input type="button" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="Yes" iconClass="dijitNoIcon"></input>
							</fieldset>
						</div> </span>
				</div>