#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

//texture samplers
uniform sampler2D u_texture; //diffuse map
uniform sampler2D u_lightmap;   //light map

//additional parameters for the shader
uniform vec2 resolution; //resolution of screen
uniform LOWP vec4 ambientColor; //ambient RGB, alpha channel is intensity

void main() {
	vec4 diffuseColor = texture2D(u_texture, v_texCoord);
	vec2 lighCoord = (gl_FragCoord.xy / resolution.xy);
	vec4 light = texture2D(u_lightmap, lighCoord);
	
	vec3 ambient = ambientColor.rgb * ambientColor.a;
	vec3 intensity = ambient + light.rgb;
 	vec3 finalColor = diffuseColor.rgb * intensity;
	
	gl_FragColor = v_color * vec4(finalColor, diffuseColor.a);
}
