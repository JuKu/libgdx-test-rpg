#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 vColor;
varying vec2 vTexCoord;

uniform mat4 u_projTrans;

//our texture samplers
uniform sampler2D u_texture; //diffuse map

void main() {
	vec4 DiffuseColor = texture2D(u_texture, v_texCoord);
	gl_FragColor = v_color * DiffuseColor;
}