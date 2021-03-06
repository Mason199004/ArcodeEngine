#version 300 es

in mediump vec2 pass_Texture;
in mediump vec4 fg_Color;

out mediump vec4 out_Color;

uniform sampler2D sampler;

void main(void) {
    out_Color = texture(sampler, pass_Texture);
    if(out_Color.r == float(1))
        out_Color.r = fg_Color.r;
    if(out_Color.g == float(1))
        out_Color.g = fg_Color.g;
    if(out_Color.b == float(1))
        out_Color.b = fg_Color.b;
    if(out_Color.a == float(0))
        discard;
}
