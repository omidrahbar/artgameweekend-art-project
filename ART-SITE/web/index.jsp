<%-- 
    Document   : index
    Created on : 7 déc. 2010, 22:00:06
    Author     : pierre
--%>
<%@page import="org.artags.site.business.Tag" %>
<%@page import="org.artags.site.service.TagService" %>
<%@page import="java.util.List" %>

<%@include file="header.jsp" %>


		<div class="inner center">

			<div class="leftcolomn float_left relative">

				<div class="absolute" style="left: 600px; top: 40px;">

					<img src="Images/fr.png" onclick="$('#video_en').hide();$('#video_fr').show();" class="pointer" alt="Français">
					<img src="Images/en.png" onclick="$('#video_fr').hide();$('#video_en').show();" class="pointer" alt="Français">
				</div>

				<h1>Discover the project</h1>
				<div id="video_en">
					<iframe src="http://player.vimeo.com/video/17528367" frameborder="0" height="385" width="640"></iframe>
				</div>
				<div id="video_fr" style="display: none;">

					<iframe src="http://player.vimeo.com/video/17581665" frameborder="0" height="385" width="640"></iframe>
				</div>

			</div>

			<div class="rightcolomn float_right relative">

				<div class="separator"></div>
				<div class="separator"></div>

				<h3>Follow Us</h3>
				<div class="footer-list">

					<ul>
						<li><a href="http://twitter.com/statuses/user_timeline/195477198.rss" class="rssfeed">RSS Feed</a></li>
						<li><a href="mailto:artag@googlegroups.com" class="email">Email</a></li>
						<li><a href="http://twitter.com/ARtagsnews" class="twitter" target="_blank">Twitter</a></li>
						<li><a href="http://www.facebook.com/pages/ARTags/146611142055814?v=wall" class="facebook" target="_blank">Facebook</a></li>
					</ul>
				</div>

			</div>

			<div style="clear: both;"></div>
			<br><br>

			<div class="panel float_left">
				<div class="pannel_inner">
					<h3>Last Tags</h3>
					<div style="padding-left: 10px;">
						<div class="panel_slide relative">

							<div class="absolute" style="left: -370px; width: 30000px;" id="is1">
                                                                                <%
                                List<Tag> listLast = TagService.instance().getLatestTags();
                                listLast = listLast.subList( 0 , 20 );
                                for (Tag t : listLast)
                                {
                    %>
								<div class="panel_inner_slider float_left">
									<img src="/thumbnail?id=<%= t.getThumbnailId()%>" alt="thumbnail" align="left">
									<h2>

										<a href="#tag_<%= t.getId()%>" rel="prettyPhoto"><%= t.getName()%></a>
										
										<div id="tag_<%= t.getId()%>" style="display: none;">
										       <div class="intro">
												Title : <strong><%= tag.getName()%></strong><br />
												Posted : <%= tag.getDate()%><br />
												Rating : <%= tag.getRating()%><br />
												Votes : <%= tag.getRatingCount()%><br />
											</div>
											<div id="box">
												<div id="preview">
													<h2>Preview</h2>
													<img src="/display?id=<%= tag.getId()%>" alt="thumbnail" width="400"/>
												</div>
												<div id="location">
													<h2>Location</h2>
													<img src="http://maps.google.com/maps/api/staticmap?zoom=6&size=400x400&markers=<%= tag.getLatitude()%>,<%= tag.getLongitude()%>&sensor=false" alt="map" />
												</div>
											</div>
										</div>
										
										<!--<a href="tag.jsp?id=<%= t.getId()%>"><%= t.getName()%> </a>-->
										<span class="h4">
											<br />
											<%= t.getRating()%>
											<br />
											<%= t.getDate()%>
											<br />
											Votes <%= t.getRatingCount()%>
											<br />
										</span>
										
									</h2>
								</div>
                    <%
                                }

                    %>

							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="panel float_left">
				<div class="pannel_inner">
					<h3>Best Tags</h3>

					<div style="padding-left: 10px;">
						<div class="panel_slide relative">
							<div class="absolute" style="left: -370px; width: 30000px;" id="is2">
                                                                                <%
                                List<Tag> listBest = TagService.instance().getBestRatedTags();
                                listBest = listBest.subList( 0 , 20 );
                                for (Tag t : listBest)
                                {
                    %>
								<div class="panel_inner_slider float_left">
									<img src="/thumbnail?id=<%= t.getThumbnailId()%>" alt="thumbnail" align="left">
									<h2>

										<a href="tag.jsp?id=<%= t.getId()%>">
                                                                                    <%= t.getName()%>
                                                                                </a>
										<span class="h4"> <br />
                                                                                <%= t.getRating()%><br />
                                                                                <%= t.getDate()%><br />
                                                                                Votes <%= t.getRatingCount()%><br />

                                                                                </span>
									</h2>
								</div>
                    <%
                                }

                    %>
							</div>
						</div>
					</div>
				</div>
			</div>

			<script type="text/javascript">
				// --
				// -- SCRIPT SLIDER
				// --
				// -- Remplacer les variables suivantes :
				// --    max   = nombre de tags affichés
				// --    speed = délai entre chaque slide en millisecondes
				// --    anim  = vitesse du slide, en millisecondes
				// --
				var ac = 1;
				var max = 20;
				var left = 0;
				var speed = 5000;
				var anim = 800;
				setTimeout('doSlider()', speed);
				function doSlider()
				{
					left = left - 360;
					ac ++;
					if(ac > max)
					{
						ac = 1;
						$('#is1').css({ left: '370px' });
						$('#is2').css({ left: '370px' });
						left = 0;
					}
					$('#is1').animate({ left: left+'px' }, anim);
					$('#is2').animate({ left: left+'px' }, anim);
					setTimeout('doSlider()', speed);
				}
			</script>

			<div style="clear: both;"></div>
			<div class="separator"></div>

<%@include file="footer.jsp" %>
