#version 330

layout(location = 0) in vec3 v_position;
layout(location = 1) in vec3 v_normal;
layout(location = 2) in vec2 v_texture;
layout(location = 3) in vec3 v_color;

out vec3 frag_position;
out vec3 frag_normal;
out vec2 frag_texture;
out vec3 frag_color;

uniform mat4 Model;
uniform mat4 View;
uniform mat4 Projection;
uniform vec3 color;
uniform float coef;

void main()
{
	frag_position = vec3(v_position.x, v_position.y, v_position.z + coef);
	frag_normal = v_normal;
	frag_texture = v_texture;
	frag_color = color;
	
	gl_Position = Projection * View * Model * vec4(vec3(v_position.x, v_position.y, v_position.z + coef), 1.0);
}