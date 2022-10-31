var options = {
			disableFields: ['autocomplete','access','header','hidden','paragraph','number'],
			actionButtons: ['save','clear'],
			controlPosition:'left',
			replaceFields: [
			    {
			      type: "select",
			      label: "Select",
			      className:"select2 form-control"
			     
			    }
			  ],
			  typeUserAttrs: {			       
				  select: {
					  parent: {
					      label: 'parent',
					      options: {
					          <s:property value="catalogueFormBuilderVals"/>
					        },
					        value: "",
					        onchange: 'retFunction();'
					    },
					    catalogueType: {
						      label: 'Catalogue Type',
						      options: {
						          <s:property value="catalogueTypeAttribute"/>
						        },
						        value: ""
						    }
					  },
					  text: {
							    isMobileAvailable: {
							      label: 'Is Mobile Available',
							      value: false,
							      type : 'checkbox'
							    }
							  },
					'checkbox-group': {
								    isMobileAvailable: {
								      label: 'Is Mobile Available',
								      value: false,
								      type : 'checkbox-group'
								    },
							  parent: {
							      label: 'parent',
							      options: {
							          <s:property value="catalogueFormBuilderVals"/>
							        },
							        value: "",
							        onchange: 'retFunction();'
							    },
							    catalogueType: {
								      label: 'Catalogue Type',
								      options: {
								          <s:property value="catalogueTypeAttribute"/>
								        },
								        value: ""
								    }
							},
							'radio-group': {
							    isMobileAvailable: {
							      label: 'Is Mobile Available',
							      value: false,
							      type : 'radio-group'
							    },
						  parent: {
						      label: 'parent',
						      options: {
						          <s:property value="catalogueFormBuilderVals"/>
						        },
						        value: "",
						        onchange: 'retFunction();'
						    },
						    catalogueType: {
							      label: 'Catalogue Type',
							      options: {
							          <s:property value="catalogueTypeAttribute"/>
							        },
							        value: ""
							    }
						},
						'date': {
						    isMobileAvailable: {
						      label: 'Is Mobile Available',
						      value: false,
						      type : 'date'
						    },
					  parent: {
					      label: 'parent',
					      options: {
					          <s:property value="catalogueFormBuilderVals"/>
					        },
					        value: "",
					        onchange: 'retFunction();'
					    }
					},
					'select': {
					    isMobileAvailable: {
					      label: 'Is Mobile Available',
					      value: false,
					      type : 'select'
					    },
				  parent: {
				      label: 'parent',
				      options: {
				          <s:property value="catalogueFormBuilderVals"/>
				        },
				        value: "",
				        onchange: 'retFunction();'
				    }
				},
				  'video': {
					    isMobileAvailable: {
					      label: 'Is Mobile Available',
					      value: false,
					      type : 'video'
					    }
					  },
				'audio': {
						    isMobileAvailable: {
						      label: 'Is Mobile Available',
						      value: false,
						      type : 'audio'
						    }
						  },
				'photo_certification': {
							    isMobileAvailable: {
							      label: 'Is Mobile Available',
							      value: false,
							      type : 'photo_certification'
							    }
							  },
				'weather_info': {
								    isMobileAvailable: {
								      label: 'Is Mobile Available',
								      value: false,
								      type : 'weather_info'
								    }
								  },
				'plotting': {
									    isMobileAvailable: {
									      label: 'Is Mobile Available',
									      value: false,
									      type : 'plotting'
									    }
									  }
			      },
			fields:[{
				  label: 'Multiple Dropdown',
				  className:'select2 form-control',
						
				  attrs: {
				    type: 'mulitSelect'
				  }
				},{
					  label: 'List',
					  attrs: {
					    type: 'listF'
					  }
					},
					{
						  label: 'Video',
						  attrs: {
						    type: 'video'
						  }
						},
						{
							  label: 'Audio',
							  attrs: {
							    type: 'audio'
							  }
							},
							{
								  label: 'Photo',
								  attrs: {
								    type: 'photo_certification'
								  }
								},
								{
									  label: 'Weather',
									  attrs: {
									    type: 'weather_info'
									  }
									},
									{
										  label: 'Plotting',
										  attrs: {
										    type: 'plotting'
										  }
										}
				],
				templates:{
					mulitSelect: function(fieldData) {
						    return {
						      field: '<select  id="'+fieldData.name+'" multiple="true" class="form-control select2" />',
						      onRender: function() {
						        //$(document.getElementById(fieldData.name)).rateYo({rating: 3.6});
						      }
						    };
						  },
						  listF: function(fieldData) {
							    return {
							      field: '<table class="table"><tr></tr></table>',
							      onRender: function() {
							        //$(document.getElementById(fieldData.name)).rateYo({rating: 3.6});
							      }
							    };
							  },
							  video: function(fieldData){
								  return "";
							  },
							  audio: function(fieldData){
								  return "";
							  },
							  photo_certification: function(fieldData){
								  return "";
							  },
							  weather_info: function(fieldData){
								  return "";
							  },
							  plotting: function(fieldData){
								  return "";
							  }
						},
			dataType:'json',
			scrollToFieldOnAdd:true,
			stickyControls: {
				
				    enable:true,
				
				    offset: {
				
				      top: 5,
				
				      bottom:'auto',
				
				      right:'auto',
				
				    },
				
				  },

			/* Trigger befor adding the field */
			onAddField: function(fieldData,fieldId) {
				
			  },
			  /*Triggers after adding the field*/
			  /* typeUserEvents: {
	                select: {
	                    onadd: function(fld) {
	                      
	                    	 $(fld).select2();
	                    }
	                }
			  }, */
			i18n: {
		        override: {
		          'en-US': {
		        	  checkboxGroup:"Checkbox",
		        	  radioGroup:"Radio Button",
		        	  select:"Dropdown"
		          }}},
		          onSave: function (evt, formData) {		        	  
		              console.log("formbuilder saved");		              
		              saveSection(formData);
		              resetFormBuilder(formBuilder,"sectionCreation",options);
		              hideSectionCreation();
		            }

				};