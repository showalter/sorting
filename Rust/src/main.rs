use sorting::profiling::*;
use sorting::setup::*;

use std::env;
use std::process;

/// Run the sort profiler application.
fn main() {
    let args: Vec<String> = env::args().collect();

    let config = Config::new(&args).unwrap_or_else(|err| {
        println!("Problem parsing arguments: {}", err);
        process::exit(1);
    });

    run(config);
}
