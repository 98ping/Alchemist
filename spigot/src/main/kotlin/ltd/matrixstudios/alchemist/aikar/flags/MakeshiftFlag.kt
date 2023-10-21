package ltd.matrixstudios.alchemist.aikar.flags

class MakeshiftFlag(val ctx: String)
{

    /*
        Check for starting parameter like qLib

        @target should relate to flag type (eg. -p, -s, -a, -cc)
        @ctx above is the popped flag
     */
    fun validate(target: String): Boolean
    {
        val simpleCtx = ctx.trim()

        return (simpleCtx.equals(target, ignoreCase = true))
    }
}