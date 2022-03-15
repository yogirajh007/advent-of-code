thefile = File.open('../../input/day-10')
$input = thefile.read.split
thefile.close
$stack = Array.new
legal_chars = {"(" => ")", "[" => "]", "{" => "}", "<" => ">",
               ")" => "(", "]" => "[", "}" => "{", ">" => ">"}
values = {")" => 3,
          "]" => 57,
          "}" => 1197,
          ">" => 25137}

$count = 0               
#puts $input.first
$input.each{|line| stack = Array.new 
    line.each_char{|char| 
    if char == '<' or char == '(' or char == '{' or char == '['
    stack.push(char)
    elsif char != legal_chars[stack.pop]
        $count = $count + values[char]
        #puts line
        break
    end}}

puts $count
#$input.each{|line| puts line.length}

# for i in 0..94
#     for i in 
    
# end

# puts $flag
