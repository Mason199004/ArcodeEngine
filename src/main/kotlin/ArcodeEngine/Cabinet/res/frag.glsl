#version 400 core

in vec2 pass_Texture;
in vec3 color;

out vec4 out_Color;

uniform sampler2D sampler;

void main(void) {
    out_Color = texture(sampler, pass_Texture);
}