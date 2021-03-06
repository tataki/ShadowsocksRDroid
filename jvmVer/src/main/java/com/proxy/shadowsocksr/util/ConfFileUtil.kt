package com.proxy.shadowsocksr.util

import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

class ConfFileUtil
{
    val SSRLocal="""

"""
    val SSRTunnel="""

"""
    val RedSocks = """base {
 log_debug = off;
 log_info = off;
 log = stderr;
 daemon = off;
 redirector = iptables;
}

redsocks {
 local_ip = 127.0.0.1;
 local_port = 8123;
 ip = 127.0.0.1;
 port = %d;
 type = socks5;
}

"""
    val PdNSdLocal = """global {
 perm_cache = 2048;
 cache_dir = "/data/data/com.proxy.shadowsocksr";
 server_ip = %s;
 server_port = %d;
 query_method = tcp_only;
 run_ipv4 = on;
 min_ttl = 15m;
 max_ttl = 1w;
 timeout = 10;
 daemon = off;
}

server {
 label = "local";
 ip = 127.0.0.1;
 port = %d;
 %s
 reject_policy = negate;
 reject_recursively = on;
 timeout = 5;
}

rr {
 name=localhost;
 reverse=on;
 a=127.0.0.1;
 owner=localhost;
 soa=localhost,root.localhost,42,86400,900,86400,86400;
}"""
    val PdNSdDirect = """global {
 perm_cache = 2048;
 cache_dir = \"/data/data/com.proxy.shadowsocksr\";
 server_ip = %s;
 server_port = %d;
 query_method = tcp_only;
 run_ipv4 = on;
 min_ttl = 15m;
 max_ttl = 1w;
 timeout = 10;
 daemon = off;
}

server {
 label = \"china-servers\";
 ip = 114.114.114.114, 112.124.47.27;
 timeout = 4;
 reject = %s;
 reject_policy = fail;
 reject_recursively = on;
 exclude = %s;
 policy = included;
 uptest = none;
 preset = on;
}

server {
 label = \"local-server\";
 ip = 127.0.0.1;
 port = %d;
 %s
 reject_policy = negate;
 reject_recursively = on;
}

rr {
 name=localhost;
 reverse=on;
 a=127.0.0.1;
 owner=localhost;
 soa=localhost,root.localhost,42,86400,900,86400,86400;
}"""

    fun writeToFile(c: String, f: File)
    {
        try
        {
            val pw = PrintWriter(f)
            pw.write(c)
            pw.flush()
            pw.close()
        }
        catch (e: FileNotFoundException)
        {
            Log.e("EXC", "config file save failed.")
        }
    }
}
