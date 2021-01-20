#version 300 es 

in mediump vec2 pass_Texture;
in mediump vec3 color;

out mediump vec4 out_Color;

uniform sampler2D sampler;

void main(void) {
    out_Color = texture(sampler, pass_Texture);
}
