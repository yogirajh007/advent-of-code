thefile = File.open('../../input/day-10')
$input = thefile.read.split
thefile.close
$stack = Array.new
legal_chars = {"(" => ")", "[" => "]", "{" => "}", "<" => ">",
               ")" => "(", "]" => "[", "}" => "{", ">" => ">"}
values = {")" => 1,
          "]" => 2,
          "}" => 3,
          ">" => 4}

$finallist = []               
#puts $input.first
$filtered = Array.new
$input.each{|line| stack = Array.new 
    flag = true
    line.each_char{|char| 
    if char == '<' or char == '(' or char == '{' or char == '['
    stack.push(char)
    elsif char != legal_chars[stack.pop]
        flag = false
        #puts line
        break
    end}
if flag
    $filtered.push(line)
end}

$filtered.each{|line| stack = Array.new
    sum = 0
    line.each_char{|char| 
        if char == '<' or char == '(' or char == '{' or char == '['
        stack.push(char)
        else
        stack.pop
        end}
    while stack.length > 0 
        theval = values[legal_chars[stack.pop]]
        sum = (5*sum + theval)
    end
$finallist.push(sum)}

def median(array)
    return nil if array.empty?
    sorted = array.sort
    len = sorted.length
    (sorted[(len - 1) / 2] + sorted[len / 2]) / 2.0
  end

puts median($finallist.sort)
